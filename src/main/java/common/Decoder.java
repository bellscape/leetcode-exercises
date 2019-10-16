package common;

@FunctionalInterface
public interface Decoder<T> {
  T decode(String input);
}
