package main

import (
	"fmt"
	"github.com/samber/lo"
	"github.com/teunw/advent-of-code-24/helpers"
	"log"
	"sort"
	"strconv"
	"strings"
)

func main() {
	day1Part1()
	day1Part2()
}

func day1Part1() {
	content := helpers.ReadLinesOrFatal("1", "1")

	leftNumbers, rightNumbers := make([]int, 0, len(content)), make([]int, 0, len(content))
	for _, line := range content {
		numbers := strings.Split(line, "   ")
		leftNumber, err1 := strconv.Atoi(numbers[0])
		rightNumber, err2 := strconv.Atoi(numbers[1])
		if err1 != nil || err2 != nil {
			log.Fatalf("could not convert numbers from %s", line)
		}
		leftNumbers = append(leftNumbers, leftNumber)
		rightNumbers = append(rightNumbers, rightNumber)
	}

	sort.Ints(leftNumbers)
	sort.Ints(rightNumbers)

	totalDistance := 0
	for i, leftNumber := range leftNumbers {
		rightNumber := rightNumbers[i]

		distance := max(leftNumber, rightNumber) - min(leftNumber, rightNumber)
		totalDistance += distance
	}

	fmt.Printf("Part 1: %d\n", totalDistance)
}

func day1Part2() {
	content := helpers.ReadLinesOrFatal("1", "1")

	leftNumbers, rightNumbers := make([]int, 0, len(content)), make([]int, 0, len(content))
	for _, line := range content {
		numbers := strings.Split(line, "   ")
		leftNumber, err1 := strconv.Atoi(numbers[0])
		rightNumber, err2 := strconv.Atoi(numbers[1])
		if err1 != nil || err2 != nil {
			log.Fatalf("could not convert numbers from %s", line)
		}
		leftNumbers = append(leftNumbers, leftNumber)
		rightNumbers = append(rightNumbers, rightNumber)
	}

	occurences := make(map[int]int)
	multiplied := 0
	for _, number := range leftNumbers {
		occurenceOfNumber, contains := occurences[number]
		if contains {
			multiplied += occurenceOfNumber * number
			continue
		}
		counted := lo.Count(rightNumbers, number)
		occurences[number] = counted
		multiplied += counted * number
	}

	fmt.Printf("Part 2: %d\n", multiplied)
}
