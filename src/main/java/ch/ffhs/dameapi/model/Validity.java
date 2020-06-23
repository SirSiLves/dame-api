package ch.ffhs.dameapi.model;


public class Validity {

    private int ruleNumber;
    private String validationMessage;
    private Boolean isValid;
    private Color lastTouched;

    public Validity() {
        this.ruleNumber = 0;
        this.validationMessage = null;
        this.isValid = false;
        this.lastTouched = null;
    }


    public Color getLastTouched() {
        return lastTouched;
    }

    public void setLastTouched(Color lastTouched) {
        this.lastTouched = lastTouched;
    }

    public int getRuleNumber() {
        return ruleNumber;
    }

    public void setRuleNumber(int ruleNumber) {
        this.ruleNumber = ruleNumber;
    }

    public String getValidationMessage() {
        return validationMessage;
    }

    public void setValidationMessage(String validationMessage) {
        this.validationMessage = validationMessage;
    }

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
}
