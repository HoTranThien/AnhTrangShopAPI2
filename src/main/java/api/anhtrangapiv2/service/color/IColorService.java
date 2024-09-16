package api.anhtrangapiv2.service.color;

import java.util.List;

import api.anhtrangapiv2.dtos.ColorDTO;
import api.anhtrangapiv2.models.Color;
import api.anhtrangapiv2.responses.ColorResponse;

public interface IColorService {
    Color getColorById(int id);
    List<ColorResponse> getAllColors();
    Color createColor(ColorDTO color);
    Color updateColor(int id, ColorDTO color);
    Color deleteColor(int id);
}
