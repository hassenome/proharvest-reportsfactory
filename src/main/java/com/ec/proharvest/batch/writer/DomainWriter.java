package com.ec.proharvest.batch.writer;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.data.repository.CrudRepository;

public class DomainWriter<T> extends RepositoryItemWriter<T> {
    private static final String saveMethodName = "save";

    public DomainWriter(CrudRepository<T, ?> repository) {
        super();
        this.setRepository(repository);
        this.setMethodName(saveMethodName);
    }
    
 
}
