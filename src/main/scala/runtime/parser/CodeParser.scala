package runtime.parser

import java.util.regex.Pattern

case class ParamDef(name: String, clazz: String)
case class ObjectDef(method_name: String, params: Array[ParamDef], return_type: String)

object CodeParser {
	def parse(code: String): ObjectDef = {
		val m = pattern.matcher(code)
		if (m.matches()) {
			val method = m.group("method")
			val params_str = m.group("params")
			val return_type = m.group("return")
			val params = params_str.split(",").map(_.trim).map { param =>
				val Array(name, clazz) = param.split(":").map(_.trim)
				ParamDef(name, clazz)
			}
			return ObjectDef(method, params, return_type)
		}
		???
	}
	private val pattern = Pattern.compile(
		"""object Solution \{\s+def (?<method>\w+)\((?<params>[^)]+)\): (?<return>\S+) = \{\s+}\s+}""",
		Pattern.MULTILINE | Pattern.DOTALL)
}
