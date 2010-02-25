package gov.nih.nci.bda.domain;

public enum PracticeStatus {
    PROBLEM("(!)"), WAIVER("(on)"), DEFERRED("(off)"), SUCCESS("(/)"), OPTIONAL("(+)"), NOT_SUCCESSFUL("(x)");

    private final String legacyDataFormat;

    PracticeStatus(String legacyDataFormat) {
        this.legacyDataFormat = legacyDataFormat;
    }

    protected static PracticeStatus random() {
        return values()[(int) (Math.random() * values().length)];
    }

    public static PracticeStatus parse(String value) {
        PracticeStatus[] all = values();
        for (int i = 0; i < all.length; i++) {
            PracticeStatus next = all[i];
            if (next.legacyDataFormat.equals(value)) { return next; }
        }

        //not a legacy format
        return valueOf(value);
    }
}
