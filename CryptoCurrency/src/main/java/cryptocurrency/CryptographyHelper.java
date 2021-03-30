package cryptocurrency;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class CryptographyHelper {

    public static String generateHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            StringBuffer hexadecimalString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hexadecimal = Integer.toHexString(0xff & hash[i]);
                if (hexadecimal.length() == 1) {
                    hexadecimalString.append('0');
                }
                hexadecimalString.append(hexadecimal);
            }

            return hexadecimalString.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static KeyPair ellipticCurveCrypto() {

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec params = new ECGenParameterSpec("secp192k1");
            keyPairGenerator.initialize(params, secureRandom);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] applyECDSASignature(PrivateKey privateKey, String input) {
        Signature signature;
        byte[] output = new byte[0];

        try {
            signature = Signature.getInstance("ECDSA", "BC");
            signature.initSign(privateKey);
            signature.update(input.getBytes());
            output = signature.sign();

        } catch (NoSuchAlgorithmException | SignatureException | NoSuchProviderException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return output;
    }

    public static boolean verifyECDSASignature(PublicKey publicKey, String data, byte[] signature) {
        try {
            Signature ecdsaSignature = Signature.getInstance("ECDSA", "BC");
            ecdsaSignature.initVerify(publicKey);
            ecdsaSignature.update(data.getBytes());
            return ecdsaSignature.verify(signature);
        } catch (NoSuchAlgorithmException | SignatureException | NoSuchProviderException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
