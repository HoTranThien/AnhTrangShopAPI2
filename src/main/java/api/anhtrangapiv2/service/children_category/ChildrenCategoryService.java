package api.anhtrangapiv2.service.children_category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import api.anhtrangapiv2.dto.ChildrenCategoryDTO;
import api.anhtrangapiv2.models.ChildrenCategory;
import api.anhtrangapiv2.models.ParentCategory;

import api.anhtrangapiv2.repositories.ChildrenCategoryRepository;
import api.anhtrangapiv2.repositories.ParentCategoryRepository;
import api.anhtrangapiv2.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChildrenCategoryService implements IChildrenCategoryService{
    @Autowired
    private final ChildrenCategoryRepository childrentCategoryRepository;
    @Autowired
    private final ParentCategoryRepository parentCategoryRepository;
    @Autowired
    private final ProductRepository productRepository;
    @Override
    public ChildrenCategory createChildrentCategory(ChildrenCategoryDTO cc) {
        ParentCategory parentCategory = parentCategoryRepository.findById(cc.getPacaId())
        .orElseThrow(()-> new RuntimeException("The parent category doesn't exist"));
        if(childrentCategoryRepository.existsByName(cc.getName())){
            throw new RuntimeException("The children category's name already exists");
        }
        else{
            ChildrenCategory newchild = ChildrenCategory.builder()
            .name(cc.getName())
            .parentCategory(parentCategory)
            .build();
            childrentCategoryRepository.save(newchild);
            return newchild;
        }
        
    }

    @Override
    public ChildrenCategory deleteChildrenCategory(int id) {
        ChildrenCategory existingCC = getChildrenCategoryById(id);
        if(productRepository.existsByChildrenCategoryId(id)){
            throw new RuntimeException("Cannot delete this category because of associated products");
        }
        else{
            childrentCategoryRepository.delete(existingCC);
            return null;
        }
    }

    @Override
    public List<ChildrenCategory> getAllChildrenCategory() {
        return childrentCategoryRepository.findAll();
    }

    @Override
    public ChildrenCategory getChildrenCategoryById(int id) {
        return childrentCategoryRepository.findById(id).orElseThrow(()->
        new RuntimeException("Children category not found with id: " + id));
    }

    @Override
    public ChildrenCategory updateChildrentCategory(int id, ChildrenCategoryDTO cc) {
        ChildrenCategory existingCC = getChildrenCategoryById(id);
        ParentCategory existingPC = parentCategoryRepository.findById(cc.getPacaId())
        .orElseThrow(()-> new RuntimeException("The parent category doesn't exist"));
        if(childrentCategoryRepository.existsByName(cc.getName())&&!(existingCC.getName()).equals(cc.getName())){
            throw new RuntimeException("The children category's name already exists");
        }
        else{
            existingCC.setName(cc.getName());
            existingCC.setParentCategory(existingPC);
            childrentCategoryRepository.save(existingCC);
            return existingCC;
        }
    }

}
