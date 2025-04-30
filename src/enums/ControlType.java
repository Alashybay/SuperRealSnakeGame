package enums;

public enum ControlType {
    ARROWS {
        @Override
        public String getDescription() { return "Use arrow keys for movement"; }
    },
    WASD {
        @Override
        public String getDescription() { return "Use W, A, S, D keys for movement"; }
    };

    public abstract String getDescription();
}
