package api.anhtrangapiv2.service.parent_category;

import java.util.List;

import api.anhtrangapiv2.dtos.ParentCategoryDTO;
import api.anhtrangapiv2.models.ParentCategory;
import api.anhtrangapiv2.responses.ParentCategoryResponse;



public interface IParentCategoryService {
    List<ParentCategoryResponse> getAllParentCategory();
    ParentCategory getParentCategoryById(int id);
    ParentCategory createParentCategory(ParentCategoryDTO pc);
    ParentCategory deleteParentCategory(int id);
    ParentCategory updateParentCategory(int id, ParentCategoryDTO pc);
}
