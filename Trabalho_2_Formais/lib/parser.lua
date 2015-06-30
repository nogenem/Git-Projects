string.split = function(str, sep)
   local sep, fields = ((not sep or sep=="") and "." or sep), {}
   str = str or ""
   local pattern = string.format("([^%s]+)", sep)
         pattern = sep=="." and sep or pattern
   str:gsub(pattern, function(c) fields[#fields+1] = c end)
   return fields
end

function isInArray(array, value)
   for i, v in pairs(array) do
      if v == value then
         return i
      end
   end
   return false
end

function sucess()
   local s = "Sucesso! Sentença foi aceita.\r\n"
   s = s.."Parse: "
   s = s.. table.concat(seq, ", ")

   error(s)
end

function my_erro(y, x, err)
   local erro = "Entrada: "
   erro = erro.. table.concat(x," ").."\r\n"
   erro = erro.."Simbolo atual: "
   erro = erro..y.."\r\n"
   erro = erro.."Erro: "
   erro = erro..err

   error(erro)
end

function parser(funcs, x)
   _G.x = string.split(x, " ")
   _G.seq = {}
   f = loadstring(funcs)
   local y,x = f()
   if y == "Z'" then
      sucess()
   else
      my_erro(y,x,"Fim esperado.")
   end
end