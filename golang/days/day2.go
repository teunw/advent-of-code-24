package main

import (
	"fmt"
	"github.com/samber/lo"
	"github.com/teunw/advent-of-code-24/helpers"
	"slices"
	"strconv"
	"strings"
)

func main() {
	day2Part1()
}

func isSafe(numbers []int, print bool) bool {
	isSortedAscending := slices.IsSortedFunc(numbers, func(a, b int) int {
		return a - b
	})
	isSortedDescending := slices.IsSortedFunc(numbers, func(a, b int) int {
		return b - a
	})

	if !isSortedDescending && !isSortedAscending {
		if print {
			fmt.Printf("Line %+v is unsafe (not sorted)\n", numbers)
		}
		return false
	}

	isSafe := true
	for idx, number := range numbers {
		if idx == len(numbers)-1 {
			break
		}
		nextNumber := numbers[idx+1]
		difference := helpers.Abs(nextNumber - number)
		if difference < 1 || difference > 3 {
			isSafe = false
			break
		}
	}
	if print {
		if isSafe {
			fmt.Printf("Line %+v is safe\n", numbers)
		} else {
			fmt.Printf("Line %+v is unsafe\n", numbers)
		}
	}
	return isSafe
}

func isSafePart2(numbers []int) bool {
	newArrays := make([][]int, 0, len(numbers))
	for idx := range numbers {
		numberCopy := make([]int, len(numbers))
		copy(numberCopy, numbers)
		numberCopy = append(numberCopy[:idx], numberCopy[idx+1:]...)
		newArrays = append(newArrays, numberCopy)
	}

	isAnySafe := lo.SomeBy(newArrays, func(item []int) bool {
		return isSafe(item, false)
	})
	fmt.Printf("Line %+v is (%+v)\n", numbers, isAnySafe)
	return isAnySafe
}

func day2Part1() {
	safeReports := 0
	safeReports2 := 0
	for _, line := range helpers.ReadLinesOrFatal("2", "1") {
		numbers := lo.Map(strings.Split(line, " "), func(item string, index int) int {
			i, err := strconv.Atoi(item)
			if err != nil {
				panic(err)
			}
			return i
		})

		if isSafe(numbers, false) {
			safeReports++
		}
		if isSafePart2(numbers) {
			safeReports2++
		}
	}

	fmt.Printf("Day 2 Part 1: %d\n", safeReports)
	fmt.Printf("Day 2 Part 2: %d\n", safeReports2)
}
