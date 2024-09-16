package api.anhtrangapiv2.service.size;


import java.util.List;

import api.anhtrangapiv2.dtos.SizeDTO;
import api.anhtrangapiv2.models.Size;
import api.anhtrangapiv2.responses.SizeResponse;

public interface ISizeService {
    Size getSizeById(int id);
    List<SizeResponse> getAllSizes();
    Size createSize(SizeDTO size);
    Size updateSize(int id,SizeDTO size);
    Size deleteSize(int id) throws Exception;
}
