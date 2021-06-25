package com.ec.proharvest.batch.writer;

import java.util.List;
import java.util.function.Function;

import org.springframework.batch.item.ItemWriter;

public class CompositeDomainWriter<T,Y> implements ItemWriter<T> {

    private DomainWriter<T> dmWriter1;
    private DomainWriter<Y> dmWriter2;
    Function<List<? extends T>, List<Y>> transformer;

    
    public CompositeDomainWriter(DomainWriter<T> dmWriter1, DomainWriter<Y> dmWriter2, Function<List<? extends T>, List<Y>> transformer) {
        this.dmWriter1 = dmWriter1;
        this.dmWriter2 = dmWriter2;
        this.transformer = transformer;
    }


    @Override
    public void write(List<? extends T> items) throws Exception {
        this.dmWriter1.write(items);
        this.dmWriter2.write(transformer.apply(items));
        
    }

}