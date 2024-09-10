package api.anhtrangapiv2.service.collection;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import api.anhtrangapiv2.dtos.CollectionDTO;
import api.anhtrangapiv2.models.Collection;

public interface ICollectionService {
    List<Collection> getAllCollection();
    Collection getCollectionById(int id);
    Collection createCollection(MultipartFile img,CollectionDTO co)throws Exception;
    Collection updateCollection(int id, CollectionDTO co, MultipartFile file) throws Exception;
    String deleteCollection(int id);
}
