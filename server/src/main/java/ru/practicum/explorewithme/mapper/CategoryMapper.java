    package ru.practicum.explorewithme.mapper;

    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.NewCategoryDto;
    import ru.practicum.explorewithme.model.Category;
    import java.util.ArrayList;
    import java.util.List;

    public class CategoryMapper {
        public static CategoryDto toCategoryDto (Category category) {
            return new CategoryDto (
                    category.getId(),
                    category.getName());
        }
        public static Category toCategory (NewCategoryDto categoryDto) {
            return new Category (
                    categoryDto.getName());
        }

        public static List<CategoryDto> toListCategoryDto(List<Category> list) {
            List<CategoryDto> listDto = new ArrayList<>();
            for (Category category : list) {
                listDto.add(toCategoryDto(category));
            }
            return listDto;
        }

    }
