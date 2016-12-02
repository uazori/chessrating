package com.millhouse.chessrating.dto.utils;

import uk.co.jemos.podam.api.AbstractRandomDataProviderStrategy;
import uk.co.jemos.podam.api.AttributeMetadata;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Vadim Ovcharuk uazori@gmail.com on 12/2/2016.
 * asda
 */
public class PlayerDataProviderStrategy extends AbstractRandomDataProviderStrategy {

    private static final Random random = new Random();

    public PlayerDataProviderStrategy() {
        super();
    }

    @Override
    public String getStringValue(AttributeMetadata attributeMetadata) {
        String str1="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        if ("name".equals(attributeMetadata.getAttributeName())) {

            StringBuilder buf = new StringBuilder();
            for (int i=0; i<5 ; i++) {
                buf.append(str1.charAt(random.nextInt(str1.length())));
            }
            return buf.toString();

        }
        if ("surname".equals(attributeMetadata.getAttributeName())) {
            StringBuilder buf = new StringBuilder();
            for (int i=0; i<7 ; i++) {
                buf.append(str1.charAt(random.nextInt(str1.length())));
            }
            return buf.toString();
        }
        return super.getStringValue(attributeMetadata);
    }


    @Override
    public Long getLong(AttributeMetadata attributeMetadata) {


        if ("id".equals(attributeMetadata.getAttributeName())) {
            return ThreadLocalRandom.current().nextLong(0,125);
        }
            return super.getLong(attributeMetadata);

    }

    @Override
    public Integer getInteger(AttributeMetadata attributeMetadata) {


        if ("rating".equals(attributeMetadata.getAttributeName())) {
            return 1 + random.nextInt(5);
        }

        return super.getInteger(attributeMetadata);
    }

}
