package helpers

import (
	"fmt"
	"io"
	"log"
	"os"
	"strings"
)

type GridCoordinate struct {
	Row     int
	Column  int
	Content rune
}

func ReadFileOrFatal(day string, part string) string {
	file, err := os.OpenFile(fmt.Sprintf("inputs/day%s-%s.txt", day, part), os.O_RDONLY, 0400)
	if err != nil {
		log.Fatalf("Could not open AoC file day %s part %s %+v", day, part, err)
	}
	fileContent, err := io.ReadAll(file)
	if err != nil {
		log.Fatalf("Could read content from AoC file %+v", err)
	}

	return strings.ReplaceAll(string(fileContent), "\r\n", "\n")
}

func ReadLinesOrFatal(day string, part string) []string {
	content := ReadFileOrFatal(day, part)
	return strings.Split(content, "\n")
}

func ReadToGridOrFatal(day string, part string) *[]GridCoordinate {
	fileContent := ReadFileOrFatal(day, part)
	lines := strings.Split(fileContent, "\n")

	amountOfChars := 0
	for _, line := range lines {
		amountOfChars += len(line)
	}

	coordinates := make([]GridCoordinate, 0, amountOfChars)
	for rowIndex, rowContent := range lines {
		for columnIndex, char := range rowContent {
			coordinates = append(coordinates, GridCoordinate{
				Row:     rowIndex,
				Column:  columnIndex,
				Content: char,
			})
		}
	}

	return &coordinates
}

func Abs[T int](x T) T {
	if x < 0 {
		return -x
	}
	return x
}
