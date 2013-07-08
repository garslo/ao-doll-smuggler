# ao-doll-smuggler

This program completes the
[challenge](https://github.com/micahalles/doll-smuggler) set forth by
Atomic Object.

I have used a dynamic programming algorithm developed for the 0/1
knapsack problem, of which the doll smuggling issue is an
instance. You can find the basic algorithm in many places, including
[Wikipedia](https://en.wikipedia.org/wiki/Knapsack_problem#0.2F1_Knapsack_Problem).

## Usage

`ao-doll-smuggler` requires [Leiningen](http://leiningen.org/). It was
created and tested with Leingingen 2.2.0 (available on
[GitHub](https://raw.github.com/technomancy/leiningen/stable/bin/lein)).

The program needs a suitably formatted input file. Specifically, any
line equivalent to `max weight: <digits>` (up to whitespace) will be
treated as the maximum weight declaration. Any line formatted as
`<alphabetic> <digits> <digits>` (up to whitespace) will be treated as
a doll listing. Any line not fitting these specifications will be
ignored. See `resources/` for examples.

To determine the optimal drug-packing schema, run `lein run
path/to/data/file.txt` in the project's root directory. To run the
tests, run `lein test` in the project's root directory.

## Options

No options, however a data file must be specified.

## Examples

```sh
$ lein run resources/test_data_1.txt
packed dolls:

name    weight   value
eddie	7	20
candice	153	200
puppy	15	60
grumpkin	42	70
dusty	43	75
marc	11	70
dorothy	50	160
randal	27	60
grumpy	22	80
anthony	13	35
luke	9	150
sally	4	50
```

### Bugs

The core of the 0/1 knapsack algorithm is implemented in the
`ao-doll-smuggler.knapsack/raw-max-value` function. It takes three
arguments: a vector containing doll information, a position, and a
weight. I discovered (too late to fix it properly) that
`raw-max-value` reports the maximum value for `(- weight 1)`, rather
than for `weight`.

I adjusted the inputs accordingly and the algorithm works, but it is a
kludge. The modifications can be found in
`src/ao-doll-smuggler/knapsack.clj` starting on line 81:

```clojure
;; There is an issue with max-value wherein the optimal value
;; for a given weight is calculated as the maximum value for
;; (- weight 1). I caught this error too late; hence we (inc
;; weight) and kludge through it.
    (not (= (max-value doll-data position (inc weight))
            (max-value doll-data (dec position) (inc weight)))))
```
