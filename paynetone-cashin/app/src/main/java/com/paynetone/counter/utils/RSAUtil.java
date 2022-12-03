package com.paynetone.counter.utils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {
    public static String signature(String plainText) {
        String privateKey =
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKOuoU1kxSwYEa20\n" +
                        "NYwmGqu/6okALGXaaVsd7O9P5gxNVyQygOzboilztZB5zjbyoV1uFH14ms75bjtD\n" +
                        "N/WkP7+iFBWafSwcWSeVkDZ4kH5GXGmfCmTDJhd7bpmkcMT5rWW6i1PvxTc1MfZB\n" +
                        "68vrZV/okA+pcwng+OEQzU0cqyKdAgMBAAECgYBoTilGK3gmkDP70IHrDIEkf4kS\n" +
                        "wsSIJ3XJ9bj+C5lar5JfY7uatJ+S8MR8C9LoWxePdU2jY06rpHGlXEF1zLQ+AVRd\n" +
                        "aB+wEMWwTrEYk/GfNy9g4IUs8/g7UwGx1rU5B1+nKHmBrZidxSt/iq4/LlSwFa7d\n" +
                        "FEEBLEmzJ1uZEPl79QJBAN7EBsudq4uM4M9CJkhTx5wBGAcaKkAIrIJvXcYbA9e8\n" +
                        "HkFKXdKd6NTx6LJooBN4iYQLf9xrbgb/kNM9vz8dxq8CQQC8Gg5A4ADyJ0RjRmx/\n" +
                        "TGeoxxfNV1RRXsdGLXQmbXESyQ7h3hFkF5yOnubM8/RusfheNeYrYSgmL9LDNf6p\n" +
                        "u75zAkEAiIYw9/dAnD0RxlPvLGKUMgsFk8y4Whg2yZXBLdl+qGf1TuRpzdvTRzae\n" +
                        "+0Bd4rD77n4xtX/tpcJZGu172Q3BpwJAUtauvEdqYzqD1ayoD4/Yfu3fP6FcEtHI\n" +
                        "tNFDu8Cq8Xj2QuaHhisX2X+FaXFujml2VhcqnKQXCGo7SZk35Aw4nQJBAI7xxL5/\n" +
                        "kHV4LFuJ0Vr4UGLjAxwriLtspWg04k9B/2sFjoI6XeAuRbG2P4Jb7W1MsUpUISaB\n" +
                        "kvqrIjijS9laBvg=";

        try {
            byte[] b1 = Base64.getMimeDecoder().decode(privateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b1);
            KeyFactory kf;
            kf = KeyFactory.getInstance("RSA");
            Signature privateSignature = Signature.getInstance("SHA256withRSA");
            privateSignature.initSign(kf.generatePrivate(spec));
            privateSignature.update(plainText.getBytes(StandardCharsets.UTF_8));
            byte[] s = privateSignature.sign();
            return Base64.getEncoder().encodeToString(s);
        } catch (NoSuchAlgorithmException | SignatureException | InvalidKeyException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return "";
    }
}
