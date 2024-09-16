package api.anhtrangapiv2.service.color;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api.anhtrangapiv2.dtos.ColorDTO;
import api.anhtrangapiv2.models.Color;
import api.anhtrangapiv2.repositories.ColorRepository;
import api.anhtrangapiv2.repositories.ProductColorRepository;
import api.anhtrangapiv2.responses.ColorResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ColorService implements IColorService{
    @Autowired
    private final ColorRepository colorRepository;
    @Autowired
    private final ProductColorRepository productColorRepository;

    @Override
    @Transactional
    public Color createColor(ColorDTO color) {
        if(colorRepository.existsByName(color.getName())){
            throw new RuntimeException("The color's name already exists");
        }
        else{
            Color newColor = Color.builder().name(color.getName()).code(color.getCode()).build();
            return colorRepository.save(newColor);
        }
        
    }

    @Override
    @Transactional
    public Color deleteColor(int id) {
        Color existingColor = getColorById(id);
        if(productColorRepository.existsByColorId(id)){
            throw new RuntimeException("Cannot delete this size because of associated products");
        }
        else{
            colorRepository.delete(existingColor);
            return existingColor;
        }

    }

    @Override
    public List<ColorResponse> getAllColors() {
        List<ColorResponse> colorResponses = colorRepository.findAll().stream()
        .map(c -> ColorResponse.builder()
        .id(c.getId())
        .name(c.getName())
        .code(c.getCode())
        .build()).toList();
        return colorResponses;
    }

    @Override
    public Color getColorById(int id) {
        return colorRepository.findById(id).orElseThrow(()->new RuntimeException("Size not found with id: " + id));
    }

    @Override
    @Transactional
    public Color updateColor(int id, ColorDTO color) {
        Color existingColor = getColorById(id);
        if(colorRepository.existsByName(color.getName())&&!(existingColor.getName()).equals(color.getName()))
        {
            throw new RuntimeException("The color's name already exists");
        }
        else{
            existingColor.setName(color.getName());
            existingColor.setCode(color.getCode());
            return colorRepository.save(existingColor);
        }
    }

}
