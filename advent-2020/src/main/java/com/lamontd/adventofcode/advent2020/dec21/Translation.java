package com.lamontd.adventofcode.advent2020.dec21;

public class Translation {
    private String nativeText;
    private String englishText;

    private Translation() { }

    public String getNativeText() {
        return nativeText;
    }

    public void setNativeText(String nativeText) {
        this.nativeText = nativeText;
    }

    public String getEnglishText() {
        return englishText;
    }

    public void setEnglishText(String englishText) {
        this.englishText = englishText;
    }

    public static class Builder {
        private String nativeText;
        private String englishText;

        public Builder nativeText(String nativeText) {
            this.nativeText = nativeText;
            return this;
        }
        public Builder englishText(String englishText) {
            this.englishText = englishText;
            return this;
        }
        public Translation build() {
            Translation translation = new Translation();
            translation.setEnglishText(this.englishText);
            translation.setNativeText(this.nativeText);
            return translation;
        }
    }
}
