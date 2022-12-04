package com.lamontd.adventofcode.advent2021.dec10;

public class SyntaxChunk {
    private SyntaxChunk innerChunk = null;
    private SyntaxDelimiter chunkOpener;
    private SyntaxDelimiter expectedCloser;
    private SyntaxDelimiter actualCloser;

    public SyntaxChunk(char opener) throws CorruptedSyntaxException {
        this.chunkOpener = SyntaxDelimiter.findDelimiter(opener);
        if (chunkOpener == null) {
            throw new IllegalArgumentException("Non-delimiter opener found");
        } else if (!this.chunkOpener.isOpener()) {
            throw new CorruptedSyntaxException(SyntaxDelimiter.OPEN_BRACKET, this.chunkOpener);
        }
        switch(chunkOpener) {
            case OPEN_BRACKET:
                this.expectedCloser = SyntaxDelimiter.CLOSE_BRACKET;
                break;
            case OPEN_CURLYBRACE:
                this.expectedCloser = SyntaxDelimiter.CLOSE_CURLYBRACE;
                break;
            case OPEN_PAREN:
                this.expectedCloser = SyntaxDelimiter.CLOSE_PAREN;
                break;
            case LESS_THAN:
                this.expectedCloser = SyntaxDelimiter.GREATER_THAN;
                break;
        }
    }

    public SyntaxDelimiter getChunkOpener() { return chunkOpener; }
    public SyntaxDelimiter getExpectedCloser() { return expectedCloser; }
}
