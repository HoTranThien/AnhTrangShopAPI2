package api.anhtrangapiv2.service.redis;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.anhtrangapiv2.responses.ProductListResponse;
import lombok.RequiredArgsConstructor;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
@RequiredArgsConstructor
public class  RedisService implements IRedisService{

    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper;

    public String getKey(String field, int id, PageRequest pageRequest){
        return String.format("%s:%s:%s:%s",field.toString(),id,pageRequest.getPageNumber(),pageRequest.getPageSize());
    }
    public String getKey(String field, PageRequest pageRequest){
        return String.format("%s:%s:%s",field.toString(),pageRequest.getPageNumber(),pageRequest.getPageSize());
    }

    @Override
    public void clear() {
        jedisPool.clear();
    }

    @Override
    public ProductListResponse findAllProducts(String key){
        Jedis jedis = jedisPool.getResource();
        try {
            if(jedis.exists(key))
            return objectMapper.readValue(jedis.get(key), ProductListResponse.class);
            else return null;
        } catch (JsonProcessingException ex) {
            return null;
        }
    }

    @Override
    public void saveAllProducts(String key, ProductListResponse products) throws Exception{
        Jedis jedis = jedisPool.getResource();
        if(!jedis.exists(key)){
            try {
                jedis.set(key, objectMapper.writeValueAsString(products));
            } catch (JsonProcessingException ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    // public String test(){
    //     Jedis jedis = jedisPool.getResource();
    //     jedis.set("abcd", "12345");
    //     return "success";
    // }
}
