public class CaseInsensitiveFactory extends  AbstractInvertedIndexFactory{
    @Override
    public CaseInsensitiveIndex createInvertedIndex() {
        return CaseInsensitiveIndex.getInstance();
    }
}
