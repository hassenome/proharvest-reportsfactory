package com.ec.proharvest.batch.writer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.repository.PagingAndSortingRepository;

public class DomainWriter<T> extends RepositoryItemWriter<T> {
    private static final String saveMethodName = "save";

    public DomainWriter(PagingAndSortingRepository<T, ?> repository) {
        super();
        this.setRepository(repository);
        this.setMethodName(saveMethodName);
    }
    
 
}
