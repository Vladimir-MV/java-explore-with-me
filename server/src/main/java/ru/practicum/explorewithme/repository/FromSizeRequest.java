    package ru.practicum.explorewithme.repository;

    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;

    public class FromSizeRequest implements Pageable {

        private int offset;
        private final int limit;
        private Sort sort;

        protected FromSizeRequest(Integer offset, Integer limit, Sort sort) {
            this.offset = offset;
            this.limit = limit;
            this.sort = sort;
        }
        public static Pageable of(Integer from, Integer size) {
            return new FromSizeRequest(from, size, Sort.unsorted());
        }
        public static Pageable of(Integer from, Integer size, Sort sort) {
            return new FromSizeRequest(from, size, sort);
        }

        @Override
        public int getPageNumber() {
            return 0;
        }

        @Override
        public int getPageSize() {
            return limit;
        }

        @Override
        public long getOffset() {
            return offset;
        }

        @Override
        public Sort getSort() {
            return sort;
        }


        @Override
        public Pageable next() {
            return new FromSizeRequest(offset + limit, limit, sort);
        }

        @Override
        public Pageable previousOrFirst() {
            return new FromSizeRequest(offset, limit, sort);
        }

        @Override
        public Pageable first() {
            return new FromSizeRequest(offset, limit, sort);
        }

        @Override
        public Pageable withPage(int pageNumber) {
            return new FromSizeRequest(offset + limit * pageNumber, limit, sort);
        }

        @Override
        public boolean hasPrevious() {
            return false;
        }

    }
