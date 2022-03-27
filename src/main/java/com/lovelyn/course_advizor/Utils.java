package com.lovelyn.course_advizor;

import java.net.URI;

public class Utils {

  public static String replaceUriLastPath(URI uri, String path) {
    final String urlString = uri.toString();
    final String urlSubString = urlString.substring(0, urlString.lastIndexOf('/'));
    return String.format("%s/%s", urlSubString, path);
  }

  public static String phoneNumberToInternationalFormat(String phoneNumber) {
    return String.format("+234%s", phoneNumber.substring(1));
  }
}
