package com.ec.proharvest.batch.reader;
import java.io.Serializable;
import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.data.repository.PagingAndSortingRepository;

public class CompositeReader implements ItemReader<Serializable>{

    private DomainReader<Serializable> dmReader1;
    private DomainReader<Serializable> dmReader2;

    public CompositeReader(DomainReader<Serializable> dmReader1, DomainReader<Serializable> dmReader2) {
        this.dmReader1 = dmReader1;
        this.dmReader2 = dmReader2;
    }

    @Override
    public Serializable read()
            throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        // fetching reader 1 results; meant to be used as the reader 2 arguments        
        Object result = dmReader1.read();
        dmReader2.setArguments(result);
        dmReader2.read();
        return null;
    }

}
 
