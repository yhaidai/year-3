package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

const size = 5
var charsCount [size]int


type CyclicBarrier struct {
	generationNumber int
	count      int
	parties    int
	trigger    *sync.Cond
}

func (b *CyclicBarrier) nextgenerationNumber() {
	b.trigger.Broadcast()
	b.count = b.parties

	b.generationNumber++
}

func (b *CyclicBarrier) await() {
	b.trigger.L.Lock()
	defer b.trigger.L.Unlock()

	generationNumber := b.generationNumber

	b.count--
	index := b.count
	if index == 0 {
		b.nextgenerationNumber()
	} else {
		for generationNumber == b.generationNumber {
			b.trigger.Wait()
		}
	}
}

func createCyclicBarrier(numberOfParties int) *CyclicBarrier {
	b := CyclicBarrier{}
	b.count = numberOfParties
	b.parties = numberOfParties
	b.trigger = sync.NewCond(&sync.Mutex{})
	return &b
}


func wasFound() bool {
	for i := 0; i < size; i++ {
		for j := i; j < size; j++ {
			for q := j; q < size; q++ {
				if (i != j && i != q && q != j) {
					if (charsCount[i] == charsCount[j] && charsCount[i] == charsCount[q]) {
						return true;
					}
				}

			}
		}
	}
	return false;
}

func dispatch(wg *sync.WaitGroup, f func()) {
	wg.Add(1)
	go func() {
		f()
		wg.Done()
	}()
}

func process(char string, line [4][10]string, cyclicBarrier *CyclicBarrier) {
	for {

		randomIndex := rand.Intn(10)
		randomChar := 'A' + rand.Intn(4)
		a := 0
		b := 0
		if char == "B" {
			b = 1
		} else if char == "C" {
			b = 2
		} else if char == "D" {
			b = 3
		}
		line[b][randomIndex] = string(randomChar)
		for i := 0; i < 10; i++ {
			if line[b][i] == "A" || line[b][i] == "B" {
				a++
			}
		}
		charsCount[b] = a
		time.Sleep(time.Millisecond * 1000)
		fmt.Printf("named:%q  line:%q    num:%d\n", char, line[b], a)
		if cyclicBarrier.count == 1 {
			fmt.Printf("\n\n")

			if (wasFound()){
				fmt.Print("end")
				break
			}

		}
		cyclicBarrier.await()
	}
}


func main() {
	wg := &sync.WaitGroup{}
	defer wg.Wait()

	parties := 4
	cyclicBarrier := createCyclicBarrier(parties)

	lines := [4][10]string{
		{"A","A","A","A","B","B","B","C","C","D"},
		{"A","A","A","B","B","C","C","C","D","D"},
		{"A","A","B","C","C","C","D","D","D","D"},
		{"A","B","B","D","B","C","C","D","D","D"}}

	for i := 0; i < parties; i++ {
		char := string('A' + i)
		dispatch(wg, func() {go process(char, lines, cyclicBarrier) })
	}

	time.Sleep(time.Second * 60)
}
