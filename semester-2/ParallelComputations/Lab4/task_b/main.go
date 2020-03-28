package main

import (
	"fmt"
	"math/rand"
	"os"
	"strings"
	"sync"
	"time"
)

func gardener(garden [][]string, m *sync.Mutex) {
	for {
		m.Lock()
		for i := 0; i < 5; i++ {
			for j := 0; j < 5; j++ {
				if garden[i][j] == "-" {
					garden[i][j] = "+"
				}
			}
		}
		m.Unlock()
		time.Sleep(4 * time.Second)
	}
}

func nature(garden [][]string, m *sync.Mutex) {
	rand.Seed(time.Now().UTC().UnixNano())
	for {
		m.Lock()
		var deadCount = rand.Intn(5)
		for i := 0; i < deadCount; i++ {
			var j = rand.Intn(5)
			var k = rand.Intn(5)
			garden[j][k] = "-"
		}
		m.Unlock()

		time.Sleep(time.Second)
	}
}

func monitor1(garden [][]string, m *sync.Mutex) {
	file, err := os.Create("matrix.txt")

	if err != nil {
		fmt.Println("Unable to create file:", err)
		os.Exit(1)
	}
	defer file.Close()

	for {
		m.Lock()
		for i := 0; i < 5; i++ {
			row := strings.Join(garden[i][:], "")
			file.WriteString(row + "\n")
		}
		m.Unlock()
		file.WriteString("\n\n\n")
		time.Sleep(time.Second)
	}
}

func monitor2(garden [][]string, m *sync.Mutex) {
	for {
		m.Lock()
		for i := 0; i < 5; i++ {
			row := strings.Join(garden[i][:], "")
			print(row + "\n")
		}
		m.Unlock()
		println()
		time.Sleep(time.Second)
	}
}

func main() {
	var garden [][]string
	var wg sync.WaitGroup
	var m sync.Mutex

	for j := 0; j < 5; j++ {
		var row []string
		for i := 0; i < 5; i++ {
			row = append(row, "+")
		}
		garden = append(garden, row)
	}

	wg.Add(4)
	go nature(garden, &m)
	go gardener(garden, &m)
	go monitor1(garden, &m)
	go monitor2(garden, &m)
	wg.Wait()
}
