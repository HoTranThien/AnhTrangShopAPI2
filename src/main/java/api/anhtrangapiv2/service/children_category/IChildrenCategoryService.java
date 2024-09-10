package api.anhtrangapiv2.service.children_category;

import java.util.List;

import api.anhtrangapiv2.dtos.ChildrenCategoryDTO;
import api.anhtrangapiv2.models.ChildrenCategory;

public interface IChildrenCategoryService {
    List<ChildrenCategory> getAllChildrenCategory();
    ChildrenCategory getChildrenCategoryById(int id);
    ChildrenCategory createChildrentCategory(ChildrenCategoryDTO cc);
    ChildrenCategory updateChildrentCategory(int id, ChildrenCategoryDTO cc);
    ChildrenCategory deleteChildrenCategory(int id);

}
