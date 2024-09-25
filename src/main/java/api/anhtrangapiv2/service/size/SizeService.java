package api.anhtrangapiv2.service.size;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.anhtrangapiv2.dtos.SizeDTO;
import api.anhtrangapiv2.models.Size;

import api.anhtrangapiv2.repositories.ProductSizeRepository;
import api.anhtrangapiv2.repositories.SizeRepository;
import api.anhtrangapiv2.responses.SizeResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SizeService implements ISizeService {

    private final SizeRepository sizeRepository;

    private final ProductSizeRepository productSizeRepository;
    @Override
    @Transactional
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
    @Transactional
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
    public List<SizeResponse> getAllSizes() {
        List<SizeResponse> sizeResponses = sizeRepository.findAll(Sort.by(Sort.Direction.ASC,"id")).stream()
        .map(s -> SizeResponse.builder()
        .id(s.getId())
        .name(s.getName())
        .build()).toList();
        return sizeResponses;
    }
    @Override
    public Size getSizeById(int id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Size not found with id: " + id));
    }
    @Override
    @Transactional
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
