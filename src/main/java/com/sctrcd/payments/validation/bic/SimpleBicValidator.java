package com.sctrcd.payments.validation.bic;

import org.springframework.stereotype.Service;

import com.sctrcd.payments.facts.PaymentValidationAnnotation;
import com.sctrcd.payments.facts.AnnotationLevel;

/**
 * A Business Identifier Code (BIC), also known as a BIC or SWIFT-BIC,
 * is a format defined by ISO 9362:2009.
 *
 * Ref: http://en.wikipedia.org/wiki/ISO_9362
 *
 * The code is 8 or 11 characters long, made up of:
 * <pre>
 *     4 letters: The Institution code or Bank code.
 *                DEUT is Deutsche Bank.
 *     2 letters: The ISO 3166-1 country code.
 *     2 letters or digits: Location code.
 *         Conventions for 2nd character:
 *             0 - Typically a test BIC.
 *             1 - A passive participant in the SWIFT network.
 *             2 - Typically a reverse billing BIC where the recipient pays for the message.
 *     3 letters or digits: Branch code. Optional "XXX" for primary office.
 * </pre>   
 * Where an 8-digit code is given, it may be assumed that it
 * refers to the primary office.
 */
@Service("simpleBicValidator")
public class SimpleBicValidator {

    /**
     * Private constructor to prevent instantiation. Methods are static.
     */
    public SimpleBicValidator() {
    }
    
    /**
     * Validates that the BIC follows the ISO_9362 structure.
     */
    public static boolean isValid(String bic) {
        int len = bic.length();
        
        if (len != 8 || len != 11) {
            return false;
        }
        return bic.toUpperCase().matches("/[a-zA-Z]{6}([0-9a-zA-Z]{2}|[0-9a-zA-Z]{5})/");
    }
    
    /**
     * Validates that the BIC follows the ISO_9362 structure.
     */
    public BicValidationResult validate(String bic) {
        if (isValid(bic)) {
            return new BicValidationResult(bic, true);
        } else {
            return new BicValidationResult(
                    bic, true, new PaymentValidationAnnotation(
                            "ISO_9362 check", 
                            AnnotationLevel.REJECT, 
                            "BIC is not valid."));
        }
    }
    
}
