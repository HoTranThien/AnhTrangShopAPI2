package api.anhtrangapiv2.service.children_category;

import java.util.List;

import org.springframework.data.domain.PageRequest;

import api.anhtrangapiv2.responses.*;
import api.anhtrangapiv2.dtos.ChildrenCategoryDTO;
import api.anhtrangapiv2.models.ChildrenCategory;

public interface IChildrenCategoryService {
    List<ChildrenCategoryResponse> getAllChildrenCategory();
    ChildrenCategoryResponse getOne(int id);
    ChildrenCategory getChildrenCategoryById(int id);
    ChildrenCategory createChildrentCategory(ChildrenCategoryDTO cc);
    ChildrenCategory updateChildrentCategory(int id, ChildrenCategoryDTO cc);
    ChildrenCategory deleteChildrenCategory(int id);
    ProductListResponse getOneWithProducts(int id,PageRequest pageRequest);
}
