package com.vincent.videocompressor;

public enum CompressQuality {
    HIGH {
        @Override
        public int compressedWidth(int originalWidth) {
            return originalWidth;
        }

        @Override
        public int compressedHeight(int originalHeight) {
            return originalHeight;
        }

        @Override
        public int compressedBitRate(int width, int height) {
            return width * height * 30;
        }

        @Override
        public int compressedBitRate(int originalBitRate) {
            return originalBitRate * 90 / 100;
        }
    },
    MEDIUM {
        @Override
        public int compressedWidth(int originalWidth) {
            return makeEven(originalWidth / 2);
        }

        @Override
        public int compressedHeight(int originalHeight) {
            return makeEven(originalHeight / 2);
        }

        @Override
        public int compressedBitRate(int width, int height) {
            return width * height * 10;
        }

        @Override
        public int compressedBitRate(int originalBitRate) {
            return originalBitRate * 80 / 100;
        }
    },
    LOW {
        @Override
        public int compressedWidth(int originalWidth) {
            return makeEven(originalWidth / 3);
        }

        @Override
        public int compressedHeight(int originalHeight) {
            return makeEven(originalHeight / 3);
        }

        @Override
        public int compressedBitRate(int width, int height) {
            return (width / 2) * (height / 2) * 10;
        }

        @Override
        public int compressedBitRate(int originalBitRate) {
            return originalBitRate * 70 / 100;
        }
    };

    public abstract int compressedWidth(int originalWidth);

    public abstract int compressedHeight(int originalHeight);

    public abstract int compressedBitRate(int width, int height);

    public abstract int compressedBitRate(int originalBitRate);

    private static int makeEven(int number) {
        return number % 2 == 0 ? number : number - 1;
    }
}
