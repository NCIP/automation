package gov.nih.nci.bda.domain;

public enum PracticeStatus {
    PROBLEM, WAIVER, DEFERRED, SUCCESS, OPTIONAL, NOT_SUCCESSFUL;

    protected static PracticeStatus random() {
        return values()[(int) (Math.random() * values().length)];
    }
}
