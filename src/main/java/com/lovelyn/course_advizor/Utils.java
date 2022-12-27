package com.lovelyn.course_advizor;

import java.net.URI;

public class Utils {

  public static String replaceUriLastPath(URI uri, String path, String protoHeader) {
    final String pathString = uri.getPath();
    final String pathSubString = pathString.substring(0, pathString.lastIndexOf('/'));
    return String.format(
      "%s://%s%s",
      protoHeader == null ? uri.getScheme() : protoHeader,
      uri.getHost(),
      String.format("%s/%s", pathSubString, path)
    );
  }

  public static String phoneNumberToInternationalFormat(String phoneNumber) {
    return String.format("+234%s", phoneNumber.substring(1));
  }
}
