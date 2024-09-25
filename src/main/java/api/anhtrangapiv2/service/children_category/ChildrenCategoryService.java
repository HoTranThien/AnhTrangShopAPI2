package api.anhtrangapiv2.service.children_category;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import api.anhtrangapiv2.dtos.ChildrenCategoryDTO;
import api.anhtrangapiv2.models.ChildrenCategory;
import api.anhtrangapiv2.models.ParentCategory;
import api.anhtrangapiv2.models.Product;
import api.anhtrangapiv2.repositories.ChildrenCategoryRepository;
import api.anhtrangapiv2.repositories.ParentCategoryRepository;
import api.anhtrangapiv2.repositories.ProductRepository;
import api.anhtrangapiv2.responses.ChildrenCategoryResponse;
import api.anhtrangapiv2.responses.ProductListResponse;
import api.anhtrangapiv2.responses.ProductResponse;
import api.anhtrangapiv2.service.product.ProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChildrenCategoryService implements IChildrenCategoryService{

    private final ChildrenCategoryRepository childrentCategoryRepository;

    private final ParentCategoryRepository parentCategoryRepository;

    private final ProductRepository productRepository;

    private final ProductService productService;
    @Override
    @Transactional
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
    public ChildrenCategoryResponse getOne(int id) {
        if(!childrentCategoryRepository.existsById(id)) return null;
        ChildrenCategory existingChildrenCategory = childrentCategoryRepository.findById(id)
        .orElseThrow();
        return ChildrenCategoryResponse.builder()
        .id(id)
        .name(existingChildrenCategory.getName())
        .pacaId(existingChildrenCategory.getParentCategory().getId()).build();
    }

    @Override
    public ProductListResponse getOneWithProducts(int id,PageRequest pageRequest) {
        if(!productRepository.existsByChildrenCategoryId(id))
        return ProductListResponse.builder()
        .products(null)
        .total(0)
        .build();
        Page<Product> productPage = productRepository.findByChildrenCategoryId(id,pageRequest);
        List<ProductResponse> productResponses = productService.convertToProductResponse(productPage.getContent());
        return ProductListResponse.builder()
        .products(productResponses)
        .total(productPage.getTotalElements())
        .build();
    }

    @Override
    @Transactional
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
    public List<ChildrenCategoryResponse> getAllChildrenCategory() {
        List<ChildrenCategoryResponse> listChildrenCategoryResponses = childrentCategoryRepository.findAll().stream()
        .map(cc ->
        ChildrenCategoryResponse.builder()
        .name(cc.getName())
        .id(cc.getId())
        .pacaId(cc.getParentCategory().getId())
        .build()).collect(Collectors.toList());
        return listChildrenCategoryResponses;
    }

    @Override
    public ChildrenCategory getChildrenCategoryById(int id) {
        return childrentCategoryRepository.findById(id).orElseThrow(()->
        new RuntimeException("Children category not found with id: " + id));
    }

    @Override
    @Transactional
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
