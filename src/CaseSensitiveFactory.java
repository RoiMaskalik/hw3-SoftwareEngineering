public class CaseSensitiveFactory extends AbstractInvertedIndexFactory{

    @Override
    public CaseSensitiveIndex createInvertedIndex() {
        return CaseSensitiveIndex.getInstance();
    }
}
