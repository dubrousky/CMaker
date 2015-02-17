package cmake.indexing;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.Processor;
import com.intellij.util.indexing.FileBasedIndex;
import com.intellij.util.indexing.FileBasedIndexImpl;
import com.intellij.util.indexing.ID;
import com.intellij.util.indexing.IndexableFileSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by alex on 1/28/15.
 */
public class CMakeFileBasedIndex extends FileBasedIndex {


    @Override
    public void iterateIndexableFiles(ContentIterator contentIterator, Project project, ProgressIndicator progressIndicator) {

    }

    @Override
    public void registerIndexableSet(IndexableFileSet indexableFileSet, Project project) {

    }

    @Override
    public void removeIndexableSet(IndexableFileSet indexableFileSet) {

    }

    @Override
    public VirtualFile findFileById(Project project, int i) {
        return null;
    }

    @NotNull
    @Override
    public <K, V> List<V> getValues(ID<K, V> id, K k, GlobalSearchScope globalSearchScope) {
        return null;
    }

    @NotNull
    @Override
    public <K, V> Collection<VirtualFile> getContainingFiles(@NotNull ID<K, V> id, @NotNull K k, @NotNull GlobalSearchScope globalSearchScope) {
        return null;
    }

    @Override
    public <K, V> boolean processValues(@NotNull ID<K, V> id, @NotNull K k, VirtualFile virtualFile, @NotNull ValueProcessor<V> valueProcessor, @NotNull GlobalSearchScope globalSearchScope) {
        return false;
    }

    @Override
    public <K, V> boolean processFilesContainingAllKeys(@NotNull ID<K, V> id, @NotNull Collection<K> collection, @NotNull GlobalSearchScope globalSearchScope, Condition<V> condition, @NotNull Processor<VirtualFile> processor) {
        return false;
    }

    @NotNull
    @Override
    public <K> Collection<K> getAllKeys(@NotNull ID<K, ?> id, @NotNull Project project) {
        return null;
    }

    @Override
    public <K> void ensureUpToDate(@NotNull ID<K, ?> id, Project project, GlobalSearchScope globalSearchScope) {

    }

    @Override
    public void requestRebuild(ID<?, ?> id, Throwable throwable) {

    }

    @Override
    public <K> void scheduleRebuild(@NotNull ID<K, ?> id, @NotNull Throwable throwable) {

    }

    @Override
    public void requestReindex(@NotNull VirtualFile virtualFile) {

    }

    @Override
    public <K, V> boolean getFilesWithKey(@NotNull ID<K, V> id, @NotNull Set<K> set, @NotNull Processor<VirtualFile> processor, @NotNull GlobalSearchScope globalSearchScope) {
        return false;
    }

    @Override
    public <K> boolean processAllKeys(@NotNull ID<K, ?> id, @NotNull Processor<K> processor, Project project) {
        return false;
    }

    @Override
    public void initComponent() {

    }

    @Override
    public void disposeComponent() {

    }
}
