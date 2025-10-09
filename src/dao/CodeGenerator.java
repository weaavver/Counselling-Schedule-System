/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.security.SecureRandom;
/**
 *
 * @author Admin
 */
public class CodeGenerator {
    private static final SecureRandom random = new SecureRandom();

    // Generates a 6-digit code (e.g., 483920)
    public static String generateCode() {
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
