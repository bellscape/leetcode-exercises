package common;

import java.lang.reflect.Type;

public interface Judge {

  boolean matches(Type type, Object output, String expect);

}
