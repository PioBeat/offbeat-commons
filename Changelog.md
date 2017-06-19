# Changelog

## [0.3.4]

### New
- ``OffbeatTextView`` added setter to set font programmatically (font must be in
assets directory)
- ``FragmentViewSlidePagerAdapter``: new possibility to override
``FragmentViewSlidePagerAdapter#createItem(int)`` as hook method to instantiate
fragments when ``FragmentViewSlidePagerAdapter#getItem(int)`` is called (if
``registeredFragments`` is empty). Default is to add the fragments before.

## [0.3.3]

### New
- Typewriter-ike animation for ``OffbeatTextView``
- Support for type face in ``LabeledTextView`` (title and text)
- ``AssetsPropertyReader`` for reading a property file
- ``PinPadView`` - Component that provides a pin pad

## [0.3.1]
Project start

- LabeledTextView
- EditTextView