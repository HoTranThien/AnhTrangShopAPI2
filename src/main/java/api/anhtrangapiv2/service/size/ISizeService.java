package api.anhtrangapiv2.service.size;


import java.util.List;

import api.anhtrangapiv2.dto.SizeDTO;
import api.anhtrangapiv2.models.Size;

public interface ISizeService {
    Size getSizeById(int id);
    List<Size> getAllSizes();
    Size createSize(SizeDTO size);
    Size updateSize(int id,SizeDTO size);
    Size deleteSize(int id) throws Exception;
}
