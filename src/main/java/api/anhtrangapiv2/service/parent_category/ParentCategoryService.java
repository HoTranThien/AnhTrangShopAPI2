package api.anhtrangapiv2.service.parent_category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.dto.ParentCategoryDTO;
import api.anhtrangapiv2.models.ParentCategory;
import api.anhtrangapiv2.repositories.ParentCategoryRepository;
import api.anhtrangapiv2.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ParentCategoryService implements IParentCategoryService{
    @Autowired
    private final ParentCategoryRepository parentCategoryRepository;
    @Autowired
    private final ProductRepository productRepository;
    
    @Override
    public ParentCategory createParentCategory(ParentCategoryDTO pc) {
        if(parentCategoryRepository.existsByName(pc.getName())){
            throw new RuntimeException("The name already exists");
        }
        else{
            ParentCategory newPC = ParentCategory.builder().name(pc.getName()).build();
            parentCategoryRepository.save(newPC);
            return newPC;
        }  
    }

    @Override
    public ParentCategory deleteParentCategory(int id) {
        ParentCategory deletedpc = getParentCategoryById(id);
        if(productRepository.existsByParentCategoryId(id)){
            throw new RuntimeException("Cannot delete this category because of associated products");
        }
        else{
            parentCategoryRepository.delete(deletedpc);
            return null;
        }
    }

    @Override
    public List<ParentCategory> getAllParentCategory() {
        return parentCategoryRepository.findAll();
    }

    @Override
    public ParentCategory getParentCategoryById(int id) {
        return parentCategoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Parent category not found with id: " + id));
    }

    @Override
    public ParentCategory updateParentCategory(int id, ParentCategoryDTO pc) {
        ParentCategory updatedpc = getParentCategoryById(id);
        if(parentCategoryRepository.existsByName(pc.getName()) && !(pc.getName()).equals(updatedpc.getName())){
            throw new RuntimeException("The name already exists");
        }
        else{
            updatedpc.setName(pc.getName());
            parentCategoryRepository.save(updatedpc);
            return updatedpc;
        }

    }

}
