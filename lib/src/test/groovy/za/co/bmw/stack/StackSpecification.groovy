package za.co.bmw.stack

import spock.lang.Specification

class StackSpecification extends Specification {
    def "Stack grows and shrinks with push and pop"() {
        setup: 'The default stack using the default constructor'
        def stack = new Stack<Long>()

        expect: 'The stack to be empty'
        stack.empty()
        and: 'The capacity of the array to be the default 20'
        stack.capacity() == 20

        when: 'Push 30 items of type Long onto the stack'
        (0l..30l).each { i ->
            stack.push(i)
        }

        then: 'There should be 30 items in the stack'
        stack.count() == 30
        and: 'The stack should not be empty'
        !stack.empty()
        and: 'The capacity doubled because it went passed the initial 20 values'
        stack.capacity() == 40

        when: 'Pop the stack 30 times'
        def values = []
        (0..30).each { i ->
            values << stack.pop()
        }

        then: 'The stack should be empty'
        stack.empty()
        and: 'The amount of items in the stack should be 0'
        stack.count() == 0;
        and: 'The popped items came back in a Last in / First out (LIFO) fashion'
        values == [
                29l, 28l, 27l, 26l, 25l, 24l, 23l, 22l, 21l, 20l,
                19l, 18l, 17l, 16l, 15l, 14l, 13l, 12l, 11l, 10l,
                 9l,  8l,  7l,  6l,  5l,  4l,  3l,  2l,  1l,  0l,
        ]
    }

    def "A stack with maxSize == -1 can grow without bounds"() {
        setup: 'A stack with maxSize == -1, and initial size of the backing storage = 5'
        def stack = new Stack(5, -1)

        expect: 'An empty stack'
        stack.empty()
        and: 'a stack with a backing storage size value of 5'
        stack.capacity() == 5

        when: 'Add 1000 items to the stack'
        (0l..1000l).each { i -> stack.push(i)}

        then: 'The stack count should have doubled 8 times to 1280 in capacity'
        stack.capacity() == 1280
        and: 'The count of items in the stack should be 1000'
        stack.count() == 1000
    }

    def "A stack with maxSize capped should throw an exception if pushed passed capacity"() {
        setup: 'A stack with maxSize == 10 and initial capacity of 8'
        def stack = new Stack(8, 10)

        expect: 'A stack with a backing storage size value of 8'
        stack.capacity() == 8

        when: 'Add 5 items to the stack'
        (0..5).each {i -> stack.push(i)}

        then: 'The stack count should be 5'
        stack.count() == 5
        and: 'The capacity of the stack should not have grown and should be still on 8'
        stack.capacity() == 8

        when: 'Push 4 more items onto the stack'
        (0..4).each { i -> stack.push(i)}

        then: 'The amount of items in the stack should be 9'
        stack.count() == 9
        and: 'The stack size should not have doubled, but be put to maxSize value which is 10'
        stack.capacity() == 10

        when: 'Push 1 more item to the stack'
        stack.push(8000)
        then: 'The count should be 10'
        stack.count() == 10

        when: 'Push another item'
        stack.push(42)
        then: 'It should throw an exception'
        thrown RuntimeException
    }
}
