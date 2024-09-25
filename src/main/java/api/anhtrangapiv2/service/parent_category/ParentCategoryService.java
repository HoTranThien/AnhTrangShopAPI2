package api.anhtrangapiv2.service.parent_category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.anhtrangapiv2.dtos.ParentCategoryDTO;
import api.anhtrangapiv2.models.ParentCategory;
import api.anhtrangapiv2.repositories.ParentCategoryRepository;
import api.anhtrangapiv2.repositories.ProductRepository;
import api.anhtrangapiv2.responses.ChildrenCategoryResponse;
import api.anhtrangapiv2.responses.ParentCategoryResponse;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ParentCategoryService implements IParentCategoryService{

    private final ParentCategoryRepository parentCategoryRepository;

    private final ProductRepository productRepository;
    
    @Override
    @Transactional
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
    @Transactional
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
    public List<ParentCategoryResponse> getAllParentCategory(){
        List<ParentCategoryResponse> parentCategoryResponses = parentCategoryRepository.findAll().stream()
        .map(pc -> {
            List<ChildrenCategoryResponse> childrenCategoryResponses = pc.getChildrenCategories().stream()
            .map(cc -> ChildrenCategoryResponse.builder()
            .id(cc.getId())
            .name(cc.getName())
            .pacaId(pc.getId())
            .build()).toList();

            return ParentCategoryResponse.builder()
            .id(pc.getId())
            .name(pc.getName())
            .childrenCategories(childrenCategoryResponses)
            .build();
        })
        .collect(Collectors.toList());
        return parentCategoryResponses;
    }

    @Override
    public ParentCategory getParentCategoryById(int id) {
        return parentCategoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Parent category not found with id: " + id));
    }

    @Override
    @Transactional
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
