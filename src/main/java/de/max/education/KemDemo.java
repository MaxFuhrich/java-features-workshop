package de.max.education;

import javax.crypto.Cipher;
import javax.crypto.KEM;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Arrays;

public class KemDemo {
    //variables for holding the public key and encapsulation message which is created by using the public key with a KEM
    public static PublicKey publicKey;
    public static byte[] encapsulationMessage;

    public static void main(String[] args) throws Exception {
        kemApi();
    }

    public static void kemApi() throws
            GeneralSecurityException {

        /*
        RECEIVER
        receiver generates the keypair to be used by KEM
         */
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("X25519");
        KeyPair kp = keyGen.generateKeyPair();
        //share public key
        publicKey = kp.getPublic();



        /*
        SENDER
        use public key in the KEM do create encapsulator and finally an encapsulated.
        retrieve a secret key through the KEM
        create an encapsulation message for the receiver so that contains the secret key by using encapsulation()
         share the encapsulation message with the receiver
         */
        KEM kemSender = KEM.getInstance("DHKEM");
        PublicKey pkR = publicKey;
        KEM.Encapsulator encapsulator = kemSender.newEncapsulator(pkR);
        KEM.Encapsulated encapsulated = encapsulator.encapsulate();
        SecretKey secS = encapsulated.key();
        System.out.println("Sender SecretKey-Algorithmus: " + secS.getAlgorithm());
        encapsulationMessage = encapsulated.encapsulation();

        /*
        use the key returned by encapsulated to derive a symmetric AES key which is used to decode the actual message.
        use the symmetric key to encrypt the actual message
        send the encrypted message
         */
        byte[] keyBytes = secS.getEncoded();

        // derive a symmetric key
        SecretKey aesKey = new SecretKeySpec(keyBytes, 0, 16, "AES");

        // "setting up" the aes encryption
        Cipher cipherEnc = Cipher.getInstance("AES/GCM/NoPadding");
        byte[] iv = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        cipherEnc.init(Cipher.ENCRYPT_MODE, aesKey, gcmSpec);

        //encrypting the actual message
        String plaintextStr = "Hello world";
        byte[] plaintext = plaintextStr.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedMessage = cipherEnc.doFinal(plaintext);
        System.out.println("Verschlüsselte Nachricht: " + Arrays.toString(encryptedMessage));



        /*
        RECEIVER
        receive the encapsulation message
        receive the encrypted message
        create a decapsulator by using the private key
        use the decapsulator on the encapsulation message in order to retrieve the secret key
        derive an aes key from the secret key
        use the  aes key in order to decrypt the message
         */
        byte[] em = encapsulationMessage;
        KEM kemReceiver = KEM.getInstance("DHKEM");
        KEM.Decapsulator decapsulator = kemReceiver.newDecapsulator(kp.getPrivate());
        SecretKey secR = decapsulator.decapsulate(em);
        System.out.println("Empfänger SecretKey-Algorithmus: " + secR.getAlgorithm());

        // derive a symmetric key
        byte[] keyBytesR = secR.getEncoded();
        SecretKey aesKeyR = new SecretKeySpec(keyBytesR, 0, 16, "AES");

        // decode the encrypted message
        Cipher cipherDec = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec gcmSpecDec = new GCMParameterSpec(128, iv);
        cipherDec.init(Cipher.DECRYPT_MODE, aesKeyR, gcmSpecDec);
        byte[] decrypted = cipherDec.doFinal(encryptedMessage);
        System.out.println("Entschlüsselte Nachricht: " + new String(decrypted, StandardCharsets.UTF_8));
    }
}
