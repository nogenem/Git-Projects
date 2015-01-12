package LibMega;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.SwingWorker;

import org.json.JSONException;
import org.json.JSONObject;

public class MegaHandler2 extends SwingWorker<Void, Void> {

	private String sid;
	private int sequence_number;
	HashMap<String,long[]> user_keys = new HashMap<String,long[]>();
	private String url, path;

	public MegaHandler2(String url, String path){
		this.url = url;
		this.path = path;
		Random rg = new Random();
		sequence_number = rg.nextInt(Integer.MAX_VALUE);
	}

	@Override
	protected Void doInBackground() throws Exception {
		setProgress(0);

		print("Download started");
		String[] s = url.split("!");
		String file_id = s[1];
		byte[] file_key = MegaCrypt.base64_url_decode_byte(s[2]);

		int[] intKey = MegaCrypt.aByte_to_aInt(file_key);
		JSONObject json = new JSONObject();
		try {
			json.put("a", "g");
			json.put("g", "1");
			json.put("p", file_id);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		JSONObject file_data = new JSONObject(api_request(json.toString()));
		int[] keyNOnce = new int[] { intKey[0] ^ intKey[4], intKey[1] ^ intKey[5], intKey[2] ^ intKey[6], intKey[3] ^ intKey[7], intKey[4], intKey[5] };
		byte[] key = MegaCrypt.aInt_to_aByte(keyNOnce[0], keyNOnce[1], keyNOnce[2], keyNOnce[3]);

		int[] iiv = new int[] { keyNOnce[4], keyNOnce[5], 0, 0 };
		byte[] iv = MegaCrypt.aInt_to_aByte(iiv);

		@SuppressWarnings("unused")
		int file_size = file_data.getInt("s");
		String attribs = (file_data.getString("at"));
		attribs = new String(MegaCrypt.aes_cbc_decrypt(MegaCrypt.base64_url_decode_byte(attribs), key));

		String file_name = attribs.substring(10,attribs.lastIndexOf("\""));
		print(file_name);
		final IvParameterSpec ivSpec = new IvParameterSpec(iv);
		final SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
		Cipher cipher = Cipher.getInstance("AES/CTR/nopadding");
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
		InputStream is = null;
		String file_url = file_data.getString("g");

		FileOutputStream fos = new FileOutputStream(path+File.separator+file_name);
		final OutputStream cos = new CipherOutputStream(fos, cipher);
		final Cipher decipher = Cipher.getInstance("AES/CTR/NoPadding");
	    decipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
		int read = 0, total = 0;
		final byte[] buffer = new byte[1048576]; //obs: testar isso!  //new byte[32767];

		try {

			URLConnection urlConn = new URL(file_url).openConnection();

			print(file_url);
			is = urlConn.getInputStream();
			while ((read = is.read(buffer)) > 0) {
				cos.write(buffer, 0, read);
				total += read;
				setProgress(100 * total / file_size);
			}
		} finally {
			try {
				cos.close();
				if (is != null) {
					is.close();
				}
			} finally {
				if (fos != null) {
					fos.close();
				}
			}
		}
		print("Download finished");
		return null;
	}

	@Override
    protected void done() {
    }

	private String api_request(String data) {
		HttpURLConnection connection = null;
		try {
			String urlString = "https://g.api.mega.co.nz/cs?id=" + sequence_number;
			if (sid != null)
				urlString += "&sid=" + sid;

			URL url = new URL(urlString);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST"); //use post method
			connection.setDoOutput(true); //we will send stuff
			connection.setDoInput(true); //we want feedback
			connection.setUseCaches(false); //no caches
			connection.setAllowUserInteraction(false);
			connection.setRequestProperty("Content-Type", "text/xml");

			OutputStream out = connection.getOutputStream();
			try {
				OutputStreamWriter wr = new OutputStreamWriter(out);
				wr.write("[" + data + "]"); //data is JSON object containing the api commands
				wr.flush();
				wr.close();
			} catch (IOException e) {
				e.printStackTrace();
			} finally { //in this case, we are ensured to close the output stream
				if (out != null)
					out.close();
			}

			InputStream in = connection.getInputStream();
			StringBuffer response = new StringBuffer();
			try {
				BufferedReader rd = new BufferedReader(new InputStreamReader(in));
				String line = "";
				while ((line = rd.readLine()) != null) {
					response.append(line);
				}
				rd.close(); //close the reader
			} catch (IOException e) {
				e.printStackTrace();
			} finally {  //in this case, we are ensured to close the input stream
				if (in != null)
					in.close();
			}

			return response.toString().substring(1, response.toString().length() - 1);


		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	public static void print(Object o) {
		System.out.println(o);
	}
}
