package api.anhtrangapiv2.service.size;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.dto.SizeDTO;
import api.anhtrangapiv2.models.Size;

import api.anhtrangapiv2.repositories.ProductSizeRepository;
import api.anhtrangapiv2.repositories.SizeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SizeService implements ISizeService {
    @Autowired
    private final SizeRepository sizeRepository;
    @Autowired
    private final ProductSizeRepository productSizeRepository;
    @Override
    public Size createSize(SizeDTO size) {
        if(sizeRepository.existsByName(size.getName())){
            throw new RuntimeException("The size's name already exists");
        }
        else{
            Size newSize = Size.builder().name(size.getName()).build();
            return sizeRepository.save(newSize);
        }
    }
    @Override
    public Size deleteSize(int id) throws Exception {
        Size existingSize = getSizeById(id);
        if(productSizeRepository.existsBySizeId(id)){
            throw new RuntimeException("Cannot delete this size because of associated products");
        }
        else{
            sizeRepository.delete(existingSize);
            return existingSize;
        }

    }
    @Override
    public List<Size> getAllSizes() {
        return sizeRepository.findAll();
    }
    @Override
    public Size getSizeById(int id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Size not found with id: " + id));
    }
    @Override
    public Size updateSize(int id, SizeDTO size) {
        Size existingSize = getSizeById(id);
        if(sizeRepository.existsByName(size.getName()) && !(existingSize.getName()).equals(size.getName())){
            throw new RuntimeException("The size's name already exists");
        }
        else{
            existingSize.setName(size.getName());
            sizeRepository.save(existingSize);
            return existingSize;
        }

    }

}
