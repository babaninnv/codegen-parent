package ru.babaninnv.codegen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

@Component
public class Console {

  public void run() {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      while (true) {
        System.out.print("Enter Some Text ('x' to exit):");
        String str = br.readLine();
        System.out.println("You just entered:" + str);
        if ("x".equalsIgnoreCase(str)) {
          System.out.println("Bye!");
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
