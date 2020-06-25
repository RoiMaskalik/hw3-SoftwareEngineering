public class CaseSensitiveIndex extends AbstractInvertedIndex {
    private static CaseSensitiveIndex caseSensitiveIndex;
    private CaseSensitiveIndex(){
        super();

    }
    public static CaseSensitiveIndex getInstance() {
        if(caseSensitiveIndex == null) {
            caseSensitiveIndex = new CaseSensitiveIndex();
            System.out.println("New CaseSensitive index is created");
        }
        else
            System.out.println("You already have a CaseSensitive index");
        return caseSensitiveIndex;
    }

        /**
         * Keeps the text as it is in order to take case sensitivity in to account
         * @param row
         * @return
         */
        @Override
        protected String sensitivity(String row) {
            return row;
        }
}
