package com.ec.proharvest.batch.reader;
import java.util.List;

import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.data.repository.PagingAndSortingRepository;

public class DomainReader<T> extends RepositoryItemReader<T> {
    private static final String defaultMethodName = "findAll";

    public DomainReader(PagingAndSortingRepository<T, ?> repository, String methodName) {
        super();
        this.setRepository(repository);
        this.setMethodName(methodName);
    }

    public DomainReader(PagingAndSortingRepository<T, ?> repository, String methodName, List<?> params) {
        super();
        this.setRepository(repository);
        this.setMethodName(methodName);
        this.setArguments(params);
    }


    public DomainReader(PagingAndSortingRepository<T, ?> repository) {
        super();
        this.setRepository(repository);
        this.setMethodName(defaultMethodName);
    }
    
 
}
