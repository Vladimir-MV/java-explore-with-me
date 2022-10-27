    package ru.practicum.explorewithme.mapper;

    import ru.practicum.explorewithme.dto.CategoryDto;
    import ru.practicum.explorewithme.dto.CompilationDto;
    import ru.practicum.explorewithme.model.Category;
    import ru.practicum.explorewithme.model.Compilation;

    import java.util.ArrayList;
    import java.util.List;

    public class CategoryMapper {
        public static CategoryDto toCategoryDto (Category category) {
            return new CategoryDto (
                    category.getId(),
                    category.getName());
        }

        public static Category toCategory (CategoryDto categoryDto) {
            return new Category (
                    categoryDto.getId(),
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
